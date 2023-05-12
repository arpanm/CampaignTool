import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDisposition } from 'app/shared/model/disposition.model';
import { getEntities as getDispositions } from 'app/entities/disposition/disposition.reducer';
import { IField } from 'app/shared/model/field.model';
import { FieldType } from 'app/shared/model/enumerations/field-type.model';
import { getEntity, updateEntity, createEntity, reset } from './field.reducer';

export const FieldUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dispositions = useAppSelector(state => state.disposition.entities);
  const fieldEntity = useAppSelector(state => state.field.entity);
  const loading = useAppSelector(state => state.field.loading);
  const updating = useAppSelector(state => state.field.updating);
  const updateSuccess = useAppSelector(state => state.field.updateSuccess);
  const fieldTypeValues = Object.keys(FieldType);

  const handleClose = () => {
    navigate('/field');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getDispositions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fieldEntity,
      ...values,
      disposition: dispositions.find(it => it.id.toString() === values.disposition.toString()),
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
          fieldType: 'Text',
          ...fieldEntity,
          disposition: fieldEntity?.disposition?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="automatedPerformanceTestingApp.field.home.createOrEditLabel" data-cy="FieldCreateUpdateHeading">
            <Translate contentKey="automatedPerformanceTestingApp.field.home.createOrEditLabel">Create or edit a Field</Translate>
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
                  id="field-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.field.fieldName')}
                id="field-fieldName"
                name="fieldName"
                data-cy="fieldName"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.field.fieldLabel')}
                id="field-fieldLabel"
                name="fieldLabel"
                data-cy="fieldLabel"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.field.fieldType')}
                id="field-fieldType"
                name="fieldType"
                data-cy="fieldType"
                type="select"
              >
                {fieldTypeValues.map(fieldType => (
                  <option value={fieldType} key={fieldType}>
                    {translate('automatedPerformanceTestingApp.FieldType.' + fieldType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.field.isActive')}
                id="field-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.field.createdBy')}
                id="field-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.field.createdAt')}
                id="field-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.field.updatedBy')}
                id="field-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.field.updatedAt')}
                id="field-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="field-disposition"
                name="disposition"
                data-cy="disposition"
                label={translate('automatedPerformanceTestingApp.field.disposition')}
                type="select"
              >
                <option value="" key="0" />
                {dispositions
                  ? dispositions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/field" replace color="info">
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

export default FieldUpdate;
