import { pictureState } from "@src/slice/picture-slice";
import { List, Progress, Typography, Row, Col, Button } from "antd";
import React, { FC, useCallback } from "react";
import { useSelector } from "react-redux";
import { CompressOutlined } from "@ant-design/icons";
import {
    CloseCircleOutlined,
    DownloadOutlined,
    Loading3QuartersOutlined,
  } from '@ant-design/icons';
import { useHttp } from "@src/utils/http";
export const FileList: FC = () => {
  const { pictures } = useSelector(pictureState);
  const client = useHttp();
  const handleCompress = useCallback(() => {
    // pictures.map(p => p.id);
    client('', {})
  }, [pictures])
  return (
    <div className="file__list">
      <List
        bordered
        dataSource={pictures}
        renderItem={(item) => (
          <List.Item className="list__item">
            <Row className="list__item-row">
              <Col span={6}>
                <Typography.Text>{item.name}</Typography.Text>
              </Col>
              <Col span={3} className="list__item-row-size">
                <Typography.Text>
                  {Math.floor(item.size / 1024)}kb
                </Typography.Text>
              </Col>
              <Col span={8}>
                <Progress />
              </Col>
              <Col span={7} className='list__item-row-operation'>
                <CloseCircleOutlined/>
              </Col>
            </Row>
          </List.Item>
        )}
        footer={
          <div className="file__list__footer">
            <Typography.Text>{`${pictures.length} files in total, 0 were successfully compressed`}</Typography.Text>
            <Button type="primary" shape="round" icon={<CompressOutlined />} onClick={handleCompress}>
              Compress
            </Button>
          </div>
        }
      />
    </div>
  );
};
