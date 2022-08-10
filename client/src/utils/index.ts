import { useCallback, useRef, useState } from "react";
import { useEffect } from "react";

import { useQueryClient, QueryKey } from "@tanstack/react-query";
export const isFalsy = (value: unknown) => (value === 0 ? false : !value);
export const isVoid = (value: unknown) =>
  value === undefined || value === null || value === "";
export const cleanObject = (object?: { [key: string]: unknown }) => {
  if (!object) return;
  const result = { ...object };
  Object.keys(object).forEach((key) => {
    const value = result[key];
    if (isVoid(value)) {
      delete result[key];
    }
  });
  return result;
};

export const useMount = (callback: () => void) => {
  useEffect(() => {
    callback();
  }, []);
};

// const debounce = (func, delay) => {
//     let timeout;

//     return (...params) => {
//         if (timeout) {
//             clearTimeout(timeout);
//         }
//         timeout = setTimeout(() => {
//             func(...params);
//         }, delay)
//     }
// }

// const log = debounce(console.log('call'), 5000);
// log();
// log();
// log();

export const useDebounce = <T>(value: T, delay?: number) => {
  const [debounceValue, setDebounceValue] = useState<T>(value);

  useEffect(() => {
    let timer = setTimeout(() => setDebounceValue(value), delay);
    return () => clearTimeout(timer);
  }, [value, delay]);
  return debounceValue;
};

export const useArray = <T>(val: T[]) => {
  const [value, setValue] = useState(val);
  const clear = useCallback(() => {
    setValue([]);
  }, []);
  const add = (item: T) =>
    useCallback(() => {
      setValue([...val, item]);
    }, [value, setValue]);
  const removeIndex = (index: number) =>
    useCallback(() => {
      value.splice(index, 1);
      setValue(value);
    }, [value, setValue]);

  return { value, clear, add, removeIndex };
};

export const useDocumentTitle = (title: string, keepOnUnmount = true) => {
  const oldTitle = useRef(document.title).current;

  useEffect(() => {
    document.title = title;
  }, [title]);

  useEffect(() => {
    return () => {
      if (!keepOnUnmount) {
        document.title = oldTitle;
      }
    };
  }, [oldTitle, keepOnUnmount]);
};

export const resetRoute = () => (window.location.href = window.location.origin);

export const useMountRef = () => {
  const mountRef = useRef(false);

  useEffect(() => {
    mountRef.current = true;
    return () => {
      mountRef.current = false;
    };
  });

  return mountRef;
};

export const isError = (value: any): value is Error => value?.message;

export const abc = ({ error }: { error: unknown }) => {
  if (isError(error)) {
  }
};

export const useConfig = (queryKey: QueryKey) => {
  const queryClient = useQueryClient();
  return {
    onSuccess: () => queryClient.invalidateQueries(queryKey),
    onMutate: (variables: any) => {
      const previousItems = queryClient.getQueryData(queryKey);
      console.log("onMutate variables", variables, previousItems);
      return { previousItems };
    },
    onError: (error: any, newItem: any, context: any) => {
      queryClient.setQueryData(queryKey, context.previousItems);
    },
  };
};
