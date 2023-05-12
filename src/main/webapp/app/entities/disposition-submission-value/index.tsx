import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DispositionSubmissionValue from './disposition-submission-value';
import DispositionSubmissionValueDetail from './disposition-submission-value-detail';
import DispositionSubmissionValueUpdate from './disposition-submission-value-update';
import DispositionSubmissionValueDeleteDialog from './disposition-submission-value-delete-dialog';

const DispositionSubmissionValueRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DispositionSubmissionValue />} />
    <Route path="new" element={<DispositionSubmissionValueUpdate />} />
    <Route path=":id">
      <Route index element={<DispositionSubmissionValueDetail />} />
      <Route path="edit" element={<DispositionSubmissionValueUpdate />} />
      <Route path="delete" element={<DispositionSubmissionValueDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DispositionSubmissionValueRoutes;
