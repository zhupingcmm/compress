import { UploadFile } from "antd/lib/upload/interface";
export interface CompressFile extends UploadFile {
  id?: number;
  data?: string;
}
