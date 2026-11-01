# ServiceHub API — Docker

## Subir ambiente
```bash
docker compose up -d --build
```

## Dev (hot-reload + debug)
```bash
docker compose -f docker-compose.yml -f docker-compose.dev.yml up
```

## Prod
```bash
docker compose -f docker-compose.yml -f docker-compose.prod.yml up -d
```

## Status
```bash
docker compose ps
```

## Logs
```bash
docker compose logs -f api
docker compose logs -f postgres
```

## Acessar banco
```bash
docker compose exec postgres psql -U ${DB_USER} -d ${DB_NAME}
```

## Parar (manter dados)
```bash
docker compose down
```

## Remover tudo (incluindo volume)
```bash
docker compose down -v
```

## Reconstruir apenas API
```bash
docker compose up -d --build api
```
