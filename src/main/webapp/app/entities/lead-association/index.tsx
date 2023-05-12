import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LeadAssociation from './lead-association';
import LeadAssociationDetail from './lead-association-detail';
import LeadAssociationUpdate from './lead-association-update';
import LeadAssociationDeleteDialog from './lead-association-delete-dialog';

const LeadAssociationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LeadAssociation />} />
    <Route path="new" element={<LeadAssociationUpdate />} />
    <Route path=":id">
      <Route index element={<LeadAssociationDetail />} />
      <Route path="edit" element={<LeadAssociationUpdate />} />
      <Route path="delete" element={<LeadAssociationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeadAssociationRoutes;
