import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TelecallerAssignment from './telecaller-assignment';
import TelecallerAssignmentDetail from './telecaller-assignment-detail';
import TelecallerAssignmentUpdate from './telecaller-assignment-update';
import TelecallerAssignmentDeleteDialog from './telecaller-assignment-delete-dialog';

const TelecallerAssignmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TelecallerAssignment />} />
    <Route path="new" element={<TelecallerAssignmentUpdate />} />
    <Route path=":id">
      <Route index element={<TelecallerAssignmentDetail />} />
      <Route path="edit" element={<TelecallerAssignmentUpdate />} />
      <Route path="delete" element={<TelecallerAssignmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TelecallerAssignmentRoutes;
