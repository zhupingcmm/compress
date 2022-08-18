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
import multer from 'multer';
import fs from 'fs';
import FormData from 'form-data';

const upload = multer({dest: 'upload/'});
interface Config extends RequestInit {}


export const start = () => {
    initLog();
    const app = express();
    app.use(bodyParser.json());
    app.use(compression());
    app.use('/',express.static('public'));


    app.use(`/${COMPRESS_SERVICE_NAME}/upload`, upload.any(), (req, res, next) => {
      log.info("request info:", req.query, req.url, req.baseUrl);
      handleUploadFile(req, res, next);
  })

    app.use(`/${COMPRESS_SERVICE_NAME}`,(req, res, next) => {
        log.info("request info:", req.query, req.url, req.baseUrl);
        handle(req, res, next);
    })

    const handleUploadFile = async (req, res, next) => {
      const filename = req.files[0].originalname;
      const mimetype = req.files[0].mimetype;
        var des_file = "./upload/" + filename;
        fs.readFile( req.files[0].path, function (err, data) {
            fs.writeFile(des_file, data, function (err) {
                if( err ){
                    console.log( err );
                }else{
                    log.info('File upload to UI server')
                }
            });
        });
        try {
          let formData = new FormData();
          const data = fs.readFileSync(des_file);

          formData.append("file", data, {
            filename: filename,
            contentType: mimetype,
          })
          const customerConfig: Config = {
            method: 'POST',
            headers: formData.getHeaders(),
            body: formData
          };
          const url = `${BASE_URL}${req?.url}`;
          const response = await fetch(url, {...customerConfig});
          req.send(await response.json());
        } catch(e) {
          log.error(e.message);
          new Error(e);
        }

        // next();
    };

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