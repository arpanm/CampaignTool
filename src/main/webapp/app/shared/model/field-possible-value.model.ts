import dayjs from 'dayjs';
import { IField } from 'app/shared/model/field.model';

export interface IFieldPossibleValue {
  id?: number;
  value?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  field?: IField | null;
}

export const defaultValue: Readonly<IFieldPossibleValue> = {
  isActive: false,
};
