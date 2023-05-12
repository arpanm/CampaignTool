import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AttributePossibleValue from './attribute-possible-value';
import AttributePossibleValueDetail from './attribute-possible-value-detail';
import AttributePossibleValueUpdate from './attribute-possible-value-update';
import AttributePossibleValueDeleteDialog from './attribute-possible-value-delete-dialog';

const AttributePossibleValueRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AttributePossibleValue />} />
    <Route path="new" element={<AttributePossibleValueUpdate />} />
    <Route path=":id">
      <Route index element={<AttributePossibleValueDetail />} />
      <Route path="edit" element={<AttributePossibleValueUpdate />} />
      <Route path="delete" element={<AttributePossibleValueDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AttributePossibleValueRoutes;
