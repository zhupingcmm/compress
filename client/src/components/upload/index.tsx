import React, { FC } from "react";
import { FileList } from "./file-list";
import { UploadFile } from "./upload-file";

export const Upload = () => {
  return (
    <div className="upload">
      <UploadFile />
      <FileList />
    </div>
  );
};
