import React, {useState } from "react";
import { FileList } from "./file-list";
import { UploadFiles } from "./upload-files";
import type { UploadFile } from 'antd/es/upload/interface';
export const Upload = () => {
  const [fileList, setFileList] = useState<UploadFile[]>([]);
  return (
    <div className="upload">
      <UploadFiles fileList={fileList} setFileList={setFileList}/>
      <FileList fileList={fileList}/>
    </div>
  );
};
