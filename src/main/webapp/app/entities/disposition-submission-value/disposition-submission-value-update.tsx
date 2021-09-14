import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDispositionSubmission } from 'app/shared/model/disposition-submission.model';
import { getEntities as getDispositionSubmissions } from 'app/entities/disposition-submission/disposition-submission.reducer';
import { IField } from 'app/shared/model/field.model';
import { getEntities as getFields } from 'app/entities/field/field.reducer';
import { IFieldPossibleValue } from 'app/shared/model/field-possible-value.model';
import { getEntities as getFieldPossibleValues } from 'app/entities/field-possible-value/field-possible-value.reducer';
import { getEntity, updateEntity, createEntity, reset } from './disposition-submission-value.reducer';
import { IDispositionSubmissionValue } from 'app/shared/model/disposition-submission-value.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DispositionSubmissionValueUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const dispositionSubmissions = useAppSelector(state => state.dispositionSubmission.entities);
  const fields = useAppSelector(state => state.field.entities);
  const fieldPossibleValues = useAppSelector(state => state.fieldPossibleValue.entities);
  const dispositionSubmissionValueEntity = useAppSelector(state => state.dispositionSubmissionValue.entity);
  const loading = useAppSelector(state => state.dispositionSubmissionValue.loading);
  const updating = useAppSelector(state => state.dispositionSubmissionValue.updating);
  const updateSuccess = useAppSelector(state => state.dispositionSubmissionValue.updateSuccess);

  const handleClose = () => {
    props.history.push('/disposition-submission-value');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDispositionSubmissions({}));
    dispatch(getFields({}));
    dispatch(getFieldPossibleValues({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...dispositionSubmissionValueEntity,
      ...values,
      dispositionSubmission: dispositionSubmissions.find(it => it.id.toString() === values.dispositionSubmissionId.toString()),
      field: fields.find(it => it.id.toString() === values.fieldId.toString()),
      possibleValue: fieldPossibleValues.find(it => it.id.toString() === values.possibleValueId.toString()),
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
          ...dispositionSubmissionValueEntity,
          dispositionSubmissionId: dispositionSubmissionValueEntity?.dispositionSubmission?.id,
          fieldId: dispositionSubmissionValueEntity?.field?.id,
          possibleValueId: dispositionSubmissionValueEntity?.possibleValue?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="campaignToolApp.dispositionSubmissionValue.home.createOrEditLabel"
            data-cy="DispositionSubmissionValueCreateUpdateHeading"
          >
            <Translate contentKey="campaignToolApp.dispositionSubmissionValue.home.createOrEditLabel">
              Create or edit a DispositionSubmissionValue
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
                  id="disposition-submission-value-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('campaignToolApp.dispositionSubmissionValue.value')}
                id="disposition-submission-value-value"
                name="value"
                data-cy="value"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.dispositionSubmissionValue.createdBy')}
                id="disposition-submission-value-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.dispositionSubmissionValue.createdAt')}
                id="disposition-submission-value-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.dispositionSubmissionValue.updatedBy')}
                id="disposition-submission-value-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.dispositionSubmissionValue.updatedAt')}
                id="disposition-submission-value-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="disposition-submission-value-dispositionSubmission"
                name="dispositionSubmissionId"
                data-cy="dispositionSubmission"
                label={translate('campaignToolApp.dispositionSubmissionValue.dispositionSubmission')}
                type="select"
              >
                <option value="" key="0" />
                {dispositionSubmissions
                  ? dispositionSubmissions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="disposition-submission-value-field"
                name="fieldId"
                data-cy="field"
                label={translate('campaignToolApp.dispositionSubmissionValue.field')}
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
              <ValidatedField
                id="disposition-submission-value-possibleValue"
                name="possibleValueId"
                data-cy="possibleValue"
                label={translate('campaignToolApp.dispositionSubmissionValue.possibleValue')}
                type="select"
              >
                <option value="" key="0" />
                {fieldPossibleValues
                  ? fieldPossibleValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/disposition-submission-value"
                replace
                color="info"
              >
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

export default DispositionSubmissionValueUpdate;
