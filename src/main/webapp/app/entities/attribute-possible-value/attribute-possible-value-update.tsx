import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAttributeKey } from 'app/shared/model/attribute-key.model';
import { getEntities as getAttributeKeys } from 'app/entities/attribute-key/attribute-key.reducer';
import { IAttributePossibleValue } from 'app/shared/model/attribute-possible-value.model';
import { getEntity, updateEntity, createEntity, reset } from './attribute-possible-value.reducer';

export const AttributePossibleValueUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const attributeKeys = useAppSelector(state => state.attributeKey.entities);
  const attributePossibleValueEntity = useAppSelector(state => state.attributePossibleValue.entity);
  const loading = useAppSelector(state => state.attributePossibleValue.loading);
  const updating = useAppSelector(state => state.attributePossibleValue.updating);
  const updateSuccess = useAppSelector(state => state.attributePossibleValue.updateSuccess);

  const handleClose = () => {
    navigate('/attribute-possible-value');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAttributeKeys({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...attributePossibleValueEntity,
      ...values,
      key: attributeKeys.find(it => it.id.toString() === values.key.toString()),
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
          ...attributePossibleValueEntity,
          key: attributePossibleValueEntity?.key?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="automatedPerformanceTestingApp.attributePossibleValue.home.createOrEditLabel"
            data-cy="AttributePossibleValueCreateUpdateHeading"
          >
            <Translate contentKey="automatedPerformanceTestingApp.attributePossibleValue.home.createOrEditLabel">
              Create or edit a AttributePossibleValue
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
                  id="attribute-possible-value-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attributePossibleValue.value')}
                id="attribute-possible-value-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attributePossibleValue.isActive')}
                id="attribute-possible-value-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attributePossibleValue.createdBy')}
                id="attribute-possible-value-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attributePossibleValue.createdAt')}
                id="attribute-possible-value-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attributePossibleValue.updatedBy')}
                id="attribute-possible-value-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attributePossibleValue.updatedAt')}
                id="attribute-possible-value-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="attribute-possible-value-key"
                name="key"
                data-cy="key"
                label={translate('automatedPerformanceTestingApp.attributePossibleValue.key')}
                type="select"
              >
                <option value="" key="0" />
                {attributeKeys
                  ? attributeKeys.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/attribute-possible-value" replace color="info">
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

export default AttributePossibleValueUpdate;
