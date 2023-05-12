import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DispositionSubmission from './disposition-submission';
import DispositionSubmissionDetail from './disposition-submission-detail';
import DispositionSubmissionUpdate from './disposition-submission-update';
import DispositionSubmissionDeleteDialog from './disposition-submission-delete-dialog';

const DispositionSubmissionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DispositionSubmission />} />
    <Route path="new" element={<DispositionSubmissionUpdate />} />
    <Route path=":id">
      <Route index element={<DispositionSubmissionDetail />} />
      <Route path="edit" element={<DispositionSubmissionUpdate />} />
      <Route path="delete" element={<DispositionSubmissionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DispositionSubmissionRoutes;
