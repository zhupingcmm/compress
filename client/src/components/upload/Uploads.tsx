import React, { useMemo } from "react";
import { Typography, Upload as UploadAnt } from "antd";
import { InboxOutlined } from "@ant-design/icons";
import { useLocation, useParams } from "react-router";
const { Dragger } = UploadAnt;

export const Upload = () => {
  const { type } = useParams();
  const compressType = useMemo(() => {
    if (type === undefined) return "PNG";
    return type.toUpperCase();
  }, [type]);
  // useLocation();
  const beforeUpload = async (file: File, fileList: File[]) => {
    console.log(file, fileList);
  };

  return (
    <div className="upload">
      <h2 className="title">Compress {compressType}</h2>
      <Typography.Text className="description">
        A free and open source file compression tool Compressor makes your files
        smaller!
      </Typography.Text>
      <Dragger
        className="dragger"
        multiple={true}
        onChange={(info) => {
          console.log("info::", info);
        }}
        beforeUpload={beforeUpload}
        // disabled={true}
        action={`http://localhost:8091/picture/1`}
        // showUploadList={false}
      >
        <p className="ant-upload-drag-icon">
          <InboxOutlined />
        </p>
        <p className="ant-upload-text">
          Click or drag file to this area to upload
        </p>
        <p className="ant-upload-hint">
          Support for a single or bulk upload. Strictly prohibit from uploading
          company data or other band files
        </p>
      </Dragger>
    </div>
  );
};
