import dayjs from 'dayjs';
import { IDispositionSubmission } from 'app/shared/model/disposition-submission.model';
import { IField } from 'app/shared/model/field.model';
import { IFieldPossibleValue } from 'app/shared/model/field-possible-value.model';

export interface IDispositionSubmissionValue {
  id?: number;
  value?: string | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  dispositionSubmission?: IDispositionSubmission | null;
  field?: IField | null;
  possibleValue?: IFieldPossibleValue | null;
}

export const defaultValue: Readonly<IDispositionSubmissionValue> = {};
