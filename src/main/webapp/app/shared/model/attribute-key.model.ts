import dayjs from 'dayjs';
import { IAttributePossibleValue } from 'app/shared/model/attribute-possible-value.model';

export interface IAttributeKey {
  id?: number;
  key?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  attributePossibleValues?: IAttributePossibleValue[] | null;
}

export const defaultValue: Readonly<IAttributeKey> = {
  isActive: false,
};
