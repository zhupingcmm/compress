import { useMutation } from "@tanstack/react-query";
import { useHttp } from "./../../utils/http";
import { useMemo } from "react";
import { UploadFile } from "antd/lib/upload/interface";
interface CompressProfile {
  height?: number;
  width?: number;
  angle?: number;
}

interface Compress {
  pictureIds?: number[];
  compressProfile?: CompressProfile;
}
export const useCompressPicture = () => {
  const client = useHttp();
  return useMutation(
    (data?: Partial<Compress>) => client("", { method: "POST", data }),
    {
      onSuccess(data: any, variables: any, context?: any) {
        // console.log(data);
      },
    }
  );
};

type UploadStatus = "normal" | "exception" | "active" | "success";

type FileStatus =
  | "Not Start"
  | "Uploading"
  | "Uploaded"
  | "Compressing"
  | "Done"
  | "Upload File Failed";

export const useFileProgress = (file: UploadFile) => {
  const uploadStatus = useMemo(() => {
    // console.log(file.status);
    let pStatus: UploadStatus = "normal";
    switch (file.status) {
      case "error":
        pStatus = "exception";
        break;
      case "uploading":
        pStatus = "active";
        break;
      case "success":
        pStatus = "success";
        break;
      default:
    }
    return pStatus;
  }, [file]);

  const fileStatus = useMemo(() => {
    let fStatus: FileStatus = "Not Start";
    if (uploadStatus === "active") {
      return "Uploading";
    }
    if (uploadStatus === "success") {
      return "Uploaded";
    }

    if (uploadStatus === "exception") {
      return "Upload File Failed";
    }

    if (uploadStatus === "normal") {
      return "Uploaded";
    }

    return fStatus;
  }, [file, uploadStatus]);

  return { fileStatus, uploadStatus };
};
