import { useCallback } from "react";
// import { useAuth } from "@src/context/auth-context";
import * as qs from "qs";
import { cleanObject } from "@src/utils/index";
import { Store } from "react-notifications-component";
// import { error, success } from "./notification";

const apiUrl = process.env.REACT_APP_API_URL;
interface Config extends RequestInit {
  data?: { [key: string]: unknown };
  token?: string;
  enableNotify?: boolean;
}
export const http = async (
  endPoint: string,
  { data, token, enableNotify, ...customerConfig }: Config = {
    enableNotify: false,
  }
) => {
  const config = {
    method: "GET",
    headers: {
      Authorization: token ? `Bearer ${token}` : "",
      "Content-Type": data ? "application/json" : "",
    },
    ...customerConfig,
  };

  if (config.method.toUpperCase() === "GET") {
    endPoint += `?${qs.stringify(cleanObject(data))}`;
  } else {
    config.body = JSON.stringify(data || {});
  }

  return window
    .fetch(`${apiUrl}/${endPoint}`, config)
    .then(async (res) => {
      const result = await res.json();
      if (res.ok) {
        return Promise.resolve(result?.data);
      } else {
        return Promise.reject(result);
      }
    })
    .catch((e) => {
      return Promise.reject(e);
    });
};

export const useHttp = () => {
  return useCallback(
    (...[endpoint, config]: Parameters<typeof http>) =>
      http(endpoint, { ...config }),
    []
  );
};

let myFavoriteNumber: string | number;
myFavoriteNumber = 9;
myFavoriteNumber = "ss";

type FavoriteNumber = string | number;

let roseFavoriteNumber: FavoriteNumber = 9;

type Person = {
  name: string;
  age: number;
};

const xiaomi: Partial<Person> = { name: "zp" };

const tom: Pick<Person, "age"> = { age: 1 };

const xiaoli: Omit<Person, "name" | "age"> = { age: 2 };

// const lili: Exclude<Person, 'name'> = {age: 8}
