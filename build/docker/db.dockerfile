FROM mongo:3
LABEL maintainer=minhluantran017@gmail.com class=demo

# ENV MONGO_INITDB_ROOT_USERNAME springboot
# ENV MONGO_INITDB_ROOT_PASSWORD springboot
ENV MONGO_INITDB_DATABASE productDb

CMD ["mongod"]