import { pictureState, updateFiles } from "@src/slice/picture-slice";
import { List, Typography, Row, Col, Button } from "antd";
import React, { FC, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { CompressOutlined } from "@ant-design/icons";
import { useHttp } from "@src/utils/http";
import { FileProgress } from "./file-progress";
export const FileList: FC = () => {
  const { pictures } = useSelector(pictureState);
  const dispatch = useDispatch();
  const client = useHttp();
  const handleCompress = useCallback(async () => {
    const uids = pictures.map((p) => p?.uid);
    const compressProfile = {
      height: 100,
      width: 100,
      angle: 45,
    };
    const result = await client("picture/compress", {
      data: { uids, compressProfile },
      method: "POST",
    });
    dispatch(updateFiles(result));
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
            <Typography.Text>{`${pictures.length} files in total, {} were successfully compressed`}</Typography.Text>
            <div>
              {/* <Button
                type="primary"
                shape="round"
                icon={<CompressOutlined />}
                onClick={handleCompress}
              >
                Setting
              </Button> */}
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
