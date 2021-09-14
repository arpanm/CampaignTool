import dayjs from 'dayjs';
import { IFieldPossibleValue } from 'app/shared/model/field-possible-value.model';
import { IDisposition } from 'app/shared/model/disposition.model';
import { FieldType } from 'app/shared/model/enumerations/field-type.model';

export interface IField {
  id?: number;
  fieldName?: string | null;
  fieldLabel?: string | null;
  fieldType?: FieldType | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  fieldPossibleValues?: IFieldPossibleValue[] | null;
  disposition?: IDisposition | null;
}

export const defaultValue: Readonly<IField> = {
  isActive: false,
};
