import dayjs from 'dayjs';
import { UploadStatus } from 'app/shared/model/enumerations/upload-status.model';

export interface ILeadUploadFile {
  id?: number;
  fileUrl?: string | null;
  uploadStatus?: UploadStatus | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
}

export const defaultValue: Readonly<ILeadUploadFile> = {};
