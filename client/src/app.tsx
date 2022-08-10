import React from "react";
import { Button, message } from "antd";
import { useHttp } from "./utils/http";
import { Header } from "@src/components/header";
import { RoutesComponent } from "./routes";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from '@tanstack/react-query-devtools'
export const App = () => {
  const queryClient = new QueryClient();
  return (
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen={false} />
      <RoutesComponent />
    </QueryClientProvider>
  );
};
