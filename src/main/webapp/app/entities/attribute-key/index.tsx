import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AttributeKey from './attribute-key';
import AttributeKeyDetail from './attribute-key-detail';
import AttributeKeyUpdate from './attribute-key-update';
import AttributeKeyDeleteDialog from './attribute-key-delete-dialog';

const AttributeKeyRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AttributeKey />} />
    <Route path="new" element={<AttributeKeyUpdate />} />
    <Route path=":id">
      <Route index element={<AttributeKeyDetail />} />
      <Route path="edit" element={<AttributeKeyUpdate />} />
      <Route path="delete" element={<AttributeKeyDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AttributeKeyRoutes;
