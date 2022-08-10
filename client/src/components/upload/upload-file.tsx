import React, { useMemo } from "react";
import { Typography, Upload as UploadAnt } from "antd";
import { InboxOutlined } from "@ant-design/icons";
import { useLocation, useParams } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import { addFile, pictureState } from "@src/slice/picture-slice";
const { Dragger } = UploadAnt;
const apiUrl = process.env.REACT_APP_API_URL;
export const UploadFile = () => {
  const { type } = useParams();
  const dispatch = useDispatch();
  const state = useSelector(pictureState);
  const compressType = useMemo(() => {
    if (type === undefined) return "PNG";
    return type.toUpperCase();
  }, [type]);
  const beforeUpload = async (file: File, fileList: File[]) => {
    console.log(file, fileList);
    dispatch(addFile(file));
  };

  console.log("pictures:", state);
  return (
    <div className="upload__file">
      <h2 className="title">Compress {compressType}</h2>
      <Typography.Text className="description">
        A free and open source file compression tool Compressor makes your files
        smaller!
      </Typography.Text>
      <Dragger
        // accept
        className="dragger"
        multiple
        beforeUpload={beforeUpload}
        // disabled={true}
        action={`${apiUrl}/picture/1`}
        showUploadList={false}
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
