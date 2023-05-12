import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeadUploadFile from './lead-upload-file';
import LeadUploadFileDetail from './lead-upload-file-detail';
import LeadUploadFileUpdate from './lead-upload-file-update';
import LeadUploadFileDeleteDialog from './lead-upload-file-delete-dialog';

const LeadUploadFileRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeadUploadFile />} />
    <Route path="new" element={<LeadUploadFileUpdate />} />
    <Route path=":id">
      <Route index element={<LeadUploadFileDetail />} />
      <Route path="edit" element={<LeadUploadFileUpdate />} />
      <Route path="delete" element={<LeadUploadFileDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeadUploadFileRoutes;
