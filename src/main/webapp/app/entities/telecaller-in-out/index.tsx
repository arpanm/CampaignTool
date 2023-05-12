import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TelecallerInOut from './telecaller-in-out';
import TelecallerInOutDetail from './telecaller-in-out-detail';
import TelecallerInOutUpdate from './telecaller-in-out-update';
import TelecallerInOutDeleteDialog from './telecaller-in-out-delete-dialog';

const TelecallerInOutRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TelecallerInOut />} />
    <Route path="new" element={<TelecallerInOutUpdate />} />
    <Route path=":id">
      <Route index element={<TelecallerInOutDetail />} />
      <Route path="edit" element={<TelecallerInOutUpdate />} />
      <Route path="delete" element={<TelecallerInOutDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TelecallerInOutRoutes;
