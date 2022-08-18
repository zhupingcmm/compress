import express from 'express';
import fetch,{ RequestInit } from 'node-fetch';

import pkg from "log4js";
import bodyParser from 'body-parser';
import compression from "compression";
import { BASE_URL, COMPRESS_SERVICE_NAME, SERVER_PORT } from "./config.js";
import * as qs from 'qs';
const { getLogger } = pkg;
const log = getLogger("startup");
import { initLog } from './log-config.js';

interface Config extends RequestInit {}


export const start = () => {
    initLog();
    const app = express();
    app.use(bodyParser.json());
    app.use(compression());
    app.use('/',express.static('public'));
    app.use(`/${COMPRESS_SERVICE_NAME}`,(req, res, next) => {
        log.info("request info:", req.query, req.url, req.baseUrl);
        handle(req, res, next);
    })
    const handle = async (request, response, next) => {
        const customerConfig: Config = {
          method: request.method,
          body: JSON.stringify(request.body),
          headers: request.headers,
        };
        try {
          const url = `${BASE_URL}${request?.url}?${qs.stringify(request.query)}`;
          log.info("start request to %s", url);
          const res = await fetch(url, { ...customerConfig });
          const data = await res.json();
          response.send(data);
        } catch (e) {
          log.error(e);
            Promise.reject(e);
        }
        next();
      };
      app.listen(SERVER_PORT, () => {
        log.info(`server is running on http://localhost:${SERVER_PORT}`);
      });
}

start();