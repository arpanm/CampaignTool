import dayjs from 'dayjs';
import { IAttribute } from 'app/shared/model/attribute.model';
import { ILocation } from 'app/shared/model/location.model';

export interface ILead {
  id?: number;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  attributes?: IAttribute[] | null;
  locations?: ILocation[] | null;
}

export const defaultValue: Readonly<ILead> = {
  isActive: false,
};
