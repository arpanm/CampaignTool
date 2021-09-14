import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './disposition.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DispositionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const dispositionEntity = useAppSelector(state => state.disposition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dispositionDetailsHeading">
          <Translate contentKey="campaignToolApp.disposition.detail.title">Disposition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dispositionEntity.id}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="campaignToolApp.disposition.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{dispositionEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campaignToolApp.disposition.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{dispositionEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="campaignToolApp.disposition.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {dispositionEntity.createdAt ? (
              <TextFormat value={dispositionEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campaignToolApp.disposition.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{dispositionEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="campaignToolApp.disposition.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {dispositionEntity.updatedAt ? (
              <TextFormat value={dispositionEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/disposition" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/disposition/${dispositionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DispositionDetail;
