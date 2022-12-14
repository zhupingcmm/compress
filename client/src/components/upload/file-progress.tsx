import React, { useCallback, useMemo } from "react";
import { Progress, Typography, Col } from "antd";
import { DeleteOutlined, DownloadOutlined } from "@ant-design/icons";
import { useFileProgress } from "./hook.util";
import { useDispatch } from "react-redux";
import { useHttp } from "@src/utils/http";
import { CompressFile } from "@src/type/type";
import { saveAs } from "file-saver";
interface FileProgressProps {
  file: CompressFile;
}
const apiUrl = process.env.REACT_APP_API_URL;
export const FileProgress = ({ file }: FileProgressProps) => {
  const { uploadStatus, fileStatus } = useFileProgress(file);
  const client = useHttp();
  const dispatch = useDispatch();
  const handleDelete = useCallback(async () => {
    const { uid } = file;
    await client(`picture/${uid}`, { method: "DELETE" });
  }, [file]);

  const handleDownload = useCallback(() => {
    const { data, name } = file;
    var blob = new Blob([data || ""], { type: "data:image/png" });
    saveAs(`${apiUrl}/picture/1`, name);
  }, [file]);

  const enabled = useMemo(() => {
    return file.data;
  }, [file]);

  return (
    <>
      <Col span={4}>
        <Typography.Text className="list__item-progress-status">
          {fileStatus}
        </Typography.Text>
      </Col>
      <Col span={5} className="list__item-row-operation">
        {enabled && (
          <DownloadOutlined className="download" onClick={handleDownload} />
        )}
        <DeleteOutlined onClick={handleDelete} />
      </Col>
    </>
  );
};
