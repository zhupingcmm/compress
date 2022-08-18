FROM node:14.16.1
WORKDIR /app
RUN ["mkdir", "public"]
RUN ["mkdir", "server"]
COPY dist/* /app/public
COPY server /app/server
RUN rm -rf /app/server/*.log
RUN rm -rf /app/server/src
EXPOSE 3002
CMD ["sh", "-c", "server/start.sh"]