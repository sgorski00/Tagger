.PHONY: logs

up:
	docker compose up -d

down:
	docker compose down

logs:
	docker compose logs -f

build:
	docker compose build --no-cache

restart:
	docker compose restart

test:
	docker exec -it tagger-java mvn test

test-class:
	docker exec -it tagger-java mvn test -Dtest=${class}

shell:
	docker exec -it tagger-java sh

psql:
	docker exec -it tagger-postgres psql -U postgres -d Tagger
