import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './lead-upload-file.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LeadUploadFileDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const leadUploadFileEntity = useAppSelector(state => state.leadUploadFile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leadUploadFileDetailsHeading">
          <Translate contentKey="campaignToolApp.leadUploadFile.detail.title">LeadUploadFile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.id}</dd>
          <dt>
            <span id="fileUrl">
              <Translate contentKey="campaignToolApp.leadUploadFile.fileUrl">File Url</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.fileUrl}</dd>
          <dt>
            <span id="uploadStatus">
              <Translate contentKey="campaignToolApp.leadUploadFile.uploadStatus">Upload Status</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.uploadStatus}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campaignToolApp.leadUploadFile.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="campaignToolApp.leadUploadFile.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {leadUploadFileEntity.createdAt ? (
              <TextFormat value={leadUploadFileEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campaignToolApp.leadUploadFile.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="campaignToolApp.leadUploadFile.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {leadUploadFileEntity.updatedAt ? (
              <TextFormat value={leadUploadFileEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/lead-upload-file" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lead-upload-file/${leadUploadFileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeadUploadFileDetail;
