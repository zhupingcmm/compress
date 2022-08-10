import { BrowserRouter, Route, Routes } from "react-router-dom";
import React, { FC } from "react";
import { Home } from "@src/containers/home/indx";
import { Header } from "@src/components/header";
import { Compress } from "@src/containers/compress";
// import { NotFound } from '@src/components/not-found';
import { Navigate } from "react-router";

export const RoutesComponent: FC = () => {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/compress/:type" element={<Compress />} />
        <Route path="*" element={<Navigate to="/" replace={true} />} />
      </Routes>
    </BrowserRouter>
  );
};
