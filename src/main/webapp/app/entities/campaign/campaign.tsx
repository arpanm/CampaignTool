import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICampaign } from 'app/shared/model/campaign.model';
import { getEntities, reset } from './campaign.reducer';

export const Campaign = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const campaignList = useAppSelector(state => state.campaign.entities);
  const loading = useAppSelector(state => state.campaign.loading);
  const totalItems = useAppSelector(state => state.campaign.totalItems);
  const links = useAppSelector(state => state.campaign.links);
  const entity = useAppSelector(state => state.campaign.entity);
  const updateSuccess = useAppSelector(state => state.campaign.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="campaign-heading" data-cy="CampaignHeading">
        <Translate contentKey="automatedPerformanceTestingApp.campaign.home.title">Campaigns</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="automatedPerformanceTestingApp.campaign.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/campaign/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="automatedPerformanceTestingApp.campaign.home.createLabel">Create new Campaign</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={campaignList ? campaignList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {campaignList && campaignList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('startDate')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.startDate">Start Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('endDate')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.endDate">End Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('type')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.type">Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.status">Status</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdAt')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.createdAt">Created At</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedAt')}>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.updatedAt">Updated At</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.disposition">Disposition</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="automatedPerformanceTestingApp.campaign.client">Client</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {campaignList.map((campaign, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/campaign/${campaign.id}`} color="link" size="sm">
                        {campaign.id}
                      </Button>
                    </td>
                    <td>{campaign.name}</td>
                    <td>{campaign.description}</td>
                    <td>
                      {campaign.startDate ? <TextFormat type="date" value={campaign.startDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{campaign.endDate ? <TextFormat type="date" value={campaign.endDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                    <td>
                      <Translate contentKey={`automatedPerformanceTestingApp.CampaignType.${campaign.type}`} />
                    </td>
                    <td>
                      <Translate contentKey={`automatedPerformanceTestingApp.CampaignApprovalStatus.${campaign.status}`} />
                    </td>
                    <td>{campaign.isActive ? 'true' : 'false'}</td>
                    <td>{campaign.createdBy}</td>
                    <td>
                      {campaign.createdAt ? <TextFormat type="date" value={campaign.createdAt} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{campaign.updatedBy}</td>
                    <td>
                      {campaign.updatedAt ? <TextFormat type="date" value={campaign.updatedAt} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {campaign.disposition ? <Link to={`/disposition/${campaign.disposition.id}`}>{campaign.disposition.id}</Link> : ''}
                    </td>
                    <td>{campaign.client ? <Link to={`/client/${campaign.client.id}`}>{campaign.client.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/campaign/${campaign.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/campaign/${campaign.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/campaign/${campaign.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="automatedPerformanceTestingApp.campaign.home.notFound">No Campaigns found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Campaign;
