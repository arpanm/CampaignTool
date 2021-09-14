import dayjs from 'dayjs';
import { IAttributeKey } from 'app/shared/model/attribute-key.model';

export interface IAttributePossibleValue {
  id?: number;
  value?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  key?: IAttributeKey | null;
}

export const defaultValue: Readonly<IAttributePossibleValue> = {
  isActive: false,
};
