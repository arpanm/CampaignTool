import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeadAssignment from './lead-assignment';
import LeadAssignmentDetail from './lead-assignment-detail';
import LeadAssignmentUpdate from './lead-assignment-update';
import LeadAssignmentDeleteDialog from './lead-assignment-delete-dialog';

const LeadAssignmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeadAssignment />} />
    <Route path="new" element={<LeadAssignmentUpdate />} />
    <Route path=":id">
      <Route index element={<LeadAssignmentDetail />} />
      <Route path="edit" element={<LeadAssignmentUpdate />} />
      <Route path="delete" element={<LeadAssignmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeadAssignmentRoutes;
