import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Telecaller from './telecaller';
import TelecallerDetail from './telecaller-detail';
import TelecallerUpdate from './telecaller-update';
import TelecallerDeleteDialog from './telecaller-delete-dialog';

const TelecallerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Telecaller />} />
    <Route path="new" element={<TelecallerUpdate />} />
    <Route path=":id">
      <Route index element={<TelecallerDetail />} />
      <Route path="edit" element={<TelecallerUpdate />} />
      <Route path="delete" element={<TelecallerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TelecallerRoutes;
