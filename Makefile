.PHONY: logs

up:
	docker compose up -d

down:
	docker compose down

logs:
	docker compose logs -f

build:
	docker compose build

restart:
	docker compose restart

test:
	docker exec -it tagger_java mvn test

test-class:
	docker exec -it tagger_java mvn test -Dtest=${class}

shell:
	docker exec -it tagger_java sh
