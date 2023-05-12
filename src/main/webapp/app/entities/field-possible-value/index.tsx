import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldPossibleValue from './field-possible-value';
import FieldPossibleValueDetail from './field-possible-value-detail';
import FieldPossibleValueUpdate from './field-possible-value-update';
import FieldPossibleValueDeleteDialog from './field-possible-value-delete-dialog';

const FieldPossibleValueRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldPossibleValue />} />
    <Route path="new" element={<FieldPossibleValueUpdate />} />
    <Route path=":id">
      <Route index element={<FieldPossibleValueDetail />} />
      <Route path="edit" element={<FieldPossibleValueUpdate />} />
      <Route path="delete" element={<FieldPossibleValueDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldPossibleValueRoutes;
