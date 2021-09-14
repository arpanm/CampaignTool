import dayjs from 'dayjs';
import { ILead } from 'app/shared/model/lead.model';

export interface ILocation {
  id?: number;
  pincode?: number | null;
  country?: string | null;
  state?: string | null;
  city?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  leads?: ILead[] | null;
}

export const defaultValue: Readonly<ILocation> = {
  isActive: false,
};
