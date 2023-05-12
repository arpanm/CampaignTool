import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IField } from 'app/shared/model/field.model';
import { getEntities as getFields } from 'app/entities/field/field.reducer';
import { IFieldPossibleValue } from 'app/shared/model/field-possible-value.model';
import { getEntity, updateEntity, createEntity, reset } from './field-possible-value.reducer';

export const FieldPossibleValueUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fields = useAppSelector(state => state.field.entities);
  const fieldPossibleValueEntity = useAppSelector(state => state.fieldPossibleValue.entity);
  const loading = useAppSelector(state => state.fieldPossibleValue.loading);
  const updating = useAppSelector(state => state.fieldPossibleValue.updating);
  const updateSuccess = useAppSelector(state => state.fieldPossibleValue.updateSuccess);

  const handleClose = () => {
    navigate('/field-possible-value');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFields({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fieldPossibleValueEntity,
      ...values,
      field: fields.find(it => it.id.toString() === values.field.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...fieldPossibleValueEntity,
          field: fieldPossibleValueEntity?.field?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="automatedPerformanceTestingApp.fieldPossibleValue.home.createOrEditLabel" data-cy="FieldPossibleValueCreateUpdateHeading">
            <Translate contentKey="automatedPerformanceTestingApp.fieldPossibleValue.home.createOrEditLabel">
              Create or edit a FieldPossibleValue
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="field-possible-value-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.fieldPossibleValue.value')}
                id="field-possible-value-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.fieldPossibleValue.isActive')}
                id="field-possible-value-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.fieldPossibleValue.createdBy')}
                id="field-possible-value-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.fieldPossibleValue.createdAt')}
                id="field-possible-value-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.fieldPossibleValue.updatedBy')}
                id="field-possible-value-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.fieldPossibleValue.updatedAt')}
                id="field-possible-value-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="field-possible-value-field"
                name="field"
                data-cy="field"
                label={translate('automatedPerformanceTestingApp.fieldPossibleValue.field')}
                type="select"
              >
                <option value="" key="0" />
                {fields
                  ? fields.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/field-possible-value" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FieldPossibleValueUpdate;
