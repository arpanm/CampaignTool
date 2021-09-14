import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './disposition-submission.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DispositionSubmissionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const dispositionSubmissionEntity = useAppSelector(state => state.dispositionSubmission.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dispositionSubmissionDetailsHeading">
          <Translate contentKey="campaignToolApp.dispositionSubmission.detail.title">DispositionSubmission</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dispositionSubmissionEntity.id}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campaignToolApp.dispositionSubmission.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{dispositionSubmissionEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="campaignToolApp.dispositionSubmission.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {dispositionSubmissionEntity.createdAt ? (
              <TextFormat value={dispositionSubmissionEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campaignToolApp.dispositionSubmission.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{dispositionSubmissionEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="campaignToolApp.dispositionSubmission.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {dispositionSubmissionEntity.updatedAt ? (
              <TextFormat value={dispositionSubmissionEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="campaignToolApp.dispositionSubmission.disposition">Disposition</Translate>
          </dt>
          <dd>{dispositionSubmissionEntity.disposition ? dispositionSubmissionEntity.disposition.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/disposition-submission" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/disposition-submission/${dispositionSubmissionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DispositionSubmissionDetail;
