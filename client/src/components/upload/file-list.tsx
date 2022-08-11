import { pictureState } from "@src/slice/picture-slice";
import { List, Progress, Typography, Row, Col, Button } from "antd";
import React, { FC, useCallback } from "react";
import { useSelector } from "react-redux";
import { CompressOutlined } from "@ant-design/icons";
import {
  CloseCircleOutlined,
  DownloadOutlined,
  Loading3QuartersOutlined,
  DeleteOutlined,
} from "@ant-design/icons";
//   <DeleteOutlined />
import { useHttp } from "@src/utils/http";
import { FileProgress } from "./file-progress";
export const FileList: FC = () => {
  const { pictures } = useSelector(pictureState);
  const client = useHttp();
  const handleCompress = useCallback(() => {
    client("", {});
  }, [pictures]);
  return (
    <div className="file__list">
      <List
        bordered
        dataSource={pictures}
        renderItem={(item) => (
          <List.Item className="list__item">
            <Row className="list__item-row">
              <Col span={7}>
                <Typography.Text>{item.name}</Typography.Text>
              </Col>
              <Col span={2} className="list__item-row-size">
                <Typography.Text>
                  {item?.size && Math.floor(item?.size / 1024)}kb
                </Typography.Text>
              </Col>
              <FileProgress file={item} />

            </Row>
          </List.Item>
        )}
        footer={
          <div className="file__list__footer">
            <Typography.Text>{`${pictures.length} files in total, 0 were successfully compressed`}</Typography.Text>
            <div>
              <Button
                type="primary"
                shape="round"
                icon={<CompressOutlined />}
                onClick={handleCompress}
              >
                Setting
              </Button>
              <Button
                type="primary"
                shape="round"
                icon={<CompressOutlined />}
                onClick={handleCompress}
              >
                Compress
              </Button>
            </div>
          </div>
        }
      />
    </div>
  );
};
