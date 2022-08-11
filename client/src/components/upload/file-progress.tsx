import React, { useCallback, useMemo } from "react";
import { List, Progress, Typography, Row, Col, Button } from "antd";
// import {UploadFile} from '@src/type';
import { UploadFile } from "antd/lib/upload/interface";
// import { } from 'antd/lib/progress/progress';
import {
    CloseCircleOutlined,
    DownloadOutlined,
    Loading3QuartersOutlined,
    DeleteOutlined,
  } from "@ant-design/icons";
import { useFileProgress } from "./hook.util";
import { useDispatch, useSelector } from "react-redux";
import { pictureState, removePicture } from "@src/slice/picture-slice";
import { useHttp } from "@src/utils/http";
interface FileProgressProps {
  file: UploadFile;
}



export const FileProgress = ({ file }: FileProgressProps) => {
  const {uploadStatus, fileStatus} = useFileProgress(file);
//   const useSelector(pictureState);
  const client = useHttp();
  const dispatch = useDispatch();
  const handleDelete = useCallback(async () => {
    const {uid} = file;
    await client(`picture/${uid}`, {method: "DELETE"});
    dispatch(removePicture(uid))

  },[file]);

  return (
    <>
      <Col span={6} className="list__item-progress">
        <Progress
          strokeColor={{
            from: "#108ee9",
            to: "#87d068",
          }}
          percent={file?.percent}
          status={uploadStatus}
        />
      </Col>
      <Col span={4}>
        <Typography.Text className="list__item-progress-status">
          {fileStatus}
        </Typography.Text>
      </Col>
      <Col span={5} className="list__item-row-operation">
            <DeleteOutlined onClick={handleDelete}/>
      </Col>
    </>
  );
};
