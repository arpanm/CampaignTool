import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeadUploadFile } from 'app/shared/model/lead-upload-file.model';
import { UploadStatus } from 'app/shared/model/enumerations/upload-status.model';
import { getEntity, updateEntity, createEntity, reset } from './lead-upload-file.reducer';

export const LeadUploadFileUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leadUploadFileEntity = useAppSelector(state => state.leadUploadFile.entity);
  const loading = useAppSelector(state => state.leadUploadFile.loading);
  const updating = useAppSelector(state => state.leadUploadFile.updating);
  const updateSuccess = useAppSelector(state => state.leadUploadFile.updateSuccess);
  const uploadStatusValues = Object.keys(UploadStatus);

  const handleClose = () => {
    navigate('/lead-upload-file');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...leadUploadFileEntity,
      ...values,
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
          uploadStatus: 'Pending',
          ...leadUploadFileEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="automatedPerformanceTestingApp.leadUploadFile.home.createOrEditLabel" data-cy="LeadUploadFileCreateUpdateHeading">
            <Translate contentKey="automatedPerformanceTestingApp.leadUploadFile.home.createOrEditLabel">
              Create or edit a LeadUploadFile
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
                  id="lead-upload-file-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadUploadFile.fileUrl')}
                id="lead-upload-file-fileUrl"
                name="fileUrl"
                data-cy="fileUrl"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadUploadFile.uploadStatus')}
                id="lead-upload-file-uploadStatus"
                name="uploadStatus"
                data-cy="uploadStatus"
                type="select"
              >
                {uploadStatusValues.map(uploadStatus => (
                  <option value={uploadStatus} key={uploadStatus}>
                    {translate('automatedPerformanceTestingApp.UploadStatus.' + uploadStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadUploadFile.createdBy')}
                id="lead-upload-file-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadUploadFile.createdAt')}
                id="lead-upload-file-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadUploadFile.updatedBy')}
                id="lead-upload-file-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadUploadFile.updatedAt')}
                id="lead-upload-file-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lead-upload-file" replace color="info">
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

export default LeadUploadFileUpdate;
