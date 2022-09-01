import React, { useCallback, useMemo, useState } from "react";
import { Typography, Upload, Modal } from "antd";
import { useParams } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import { PlusOutlined } from '@ant-design/icons';
import {
  updatePictures,
  pictureState
} from "@src/slice/picture-slice";
import type { UploadFile } from 'antd/es/upload/interface';
import type { RcFile, UploadProps } from 'antd/es/upload';
import { useHttp } from "@src/utils/http";
const apiUrl = process.env.REACT_APP_API_URL;

interface UploadFilesProps {
  fileList: UploadFile[],
  setFileList: (files: UploadFile[]) => void
}

export const UploadFiles = ({ fileList, setFileList }: UploadFilesProps) => {
  const { type } = useParams();
  const dispatch = useDispatch();
  const state = useSelector(pictureState);
  const client = useHttp();
  
  const compressType = useMemo(() => {
    if (type === undefined) return "PNG";
    return type.toUpperCase();
  }, [type]);

  const handleChange: UploadProps['onChange'] = ({fileList: newFileList}) => {
    setFileList(newFileList);
    const pictures = newFileList.map(({name, size, type, lastModified, uid}) => {
      return {
        name,
        size,
        type,
        lastModified,
        uid
      }
    });
    dispatch(updatePictures(pictures));
  };
  const handlePreview = useCallback((file: UploadFile) => {

  }, []);

  const handleRemove = async (file: UploadFile) => {
    const {name, status} = file;
    if (status === 'done') {
      await client(`picture/${name}`, { method: 'DELETE' })
    }
   
  }

  const url = useMemo(() => {
    return process.env.NODE_ENV === 'development' ? `${apiUrl}/picture/1` : `${apiUrl}/picture/1/upload`;
  }, []);
  return (
    <div className="upload__file">
      <h2 className="title">Compress {compressType}</h2>
      <Typography.Text className="description">
        A free and open source file compression tool Compressor makes your files
        smaller!
      </Typography.Text>
      <Upload
        action={url}
        listType="picture-card"
        fileList={fileList}
        onPreview={handlePreview}
        onChange={handleChange}
        onRemove={handleRemove}
      >

        <div>
          <PlusOutlined />
          <div style={{ marginTop: 8 }}>Upload</div>
        </div>
      </Upload>
      <Modal visible={false} title={''} footer={null} onCancel={() => {}}>
        <img alt="example" style={{ width: '100%' }} src={''} />
      </Modal>
    </div>
  );
};
