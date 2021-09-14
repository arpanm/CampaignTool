import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDisposition } from 'app/shared/model/disposition.model';
import { getEntities as getDispositions } from 'app/entities/disposition/disposition.reducer';
import { getEntity, updateEntity, createEntity, reset } from './field.reducer';
import { IField } from 'app/shared/model/field.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FieldUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const dispositions = useAppSelector(state => state.disposition.entities);
  const fieldEntity = useAppSelector(state => state.field.entity);
  const loading = useAppSelector(state => state.field.loading);
  const updating = useAppSelector(state => state.field.updating);
  const updateSuccess = useAppSelector(state => state.field.updateSuccess);

  const handleClose = () => {
    props.history.push('/field');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
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
      disposition: dispositions.find(it => it.id.toString() === values.dispositionId.toString()),
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
          ...fieldEntity,
          fieldType: 'Text',
          dispositionId: fieldEntity?.disposition?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campaignToolApp.field.home.createOrEditLabel" data-cy="FieldCreateUpdateHeading">
            <Translate contentKey="campaignToolApp.field.home.createOrEditLabel">Create or edit a Field</Translate>
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
                label={translate('campaignToolApp.field.fieldName')}
                id="field-fieldName"
                name="fieldName"
                data-cy="fieldName"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.field.fieldLabel')}
                id="field-fieldLabel"
                name="fieldLabel"
                data-cy="fieldLabel"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.field.fieldType')}
                id="field-fieldType"
                name="fieldType"
                data-cy="fieldType"
                type="select"
              >
                <option value="Text">{translate('campaignToolApp.FieldType.Text')}</option>
                <option value="BigText">{translate('campaignToolApp.FieldType.BigText')}</option>
                <option value="Date">{translate('campaignToolApp.FieldType.Date')}</option>
                <option value="Number">{translate('campaignToolApp.FieldType.Number')}</option>
                <option value="Fraction">{translate('campaignToolApp.FieldType.Fraction')}</option>
                <option value="Pincode">{translate('campaignToolApp.FieldType.Pincode')}</option>
                <option value="Email">{translate('campaignToolApp.FieldType.Email')}</option>
                <option value="Phone">{translate('campaignToolApp.FieldType.Phone')}</option>
                <option value="DropDown">{translate('campaignToolApp.FieldType.DropDown')}</option>
                <option value="RadioButton">{translate('campaignToolApp.FieldType.RadioButton')}</option>
                <option value="CheckBox">{translate('campaignToolApp.FieldType.CheckBox')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('campaignToolApp.field.isActive')}
                id="field-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('campaignToolApp.field.createdBy')}
                id="field-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.field.createdAt')}
                id="field-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.field.updatedBy')}
                id="field-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.field.updatedAt')}
                id="field-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="field-disposition"
                name="dispositionId"
                data-cy="disposition"
                label={translate('campaignToolApp.field.disposition')}
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
