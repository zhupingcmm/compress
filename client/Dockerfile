FROM node:14.16.1
WORKDIR /app
#RUN ["mkdir", "public"]
RUN ["mkdir", "server"]
COPY server /app/server
COPY dist/* /app/server/public
RUN rm -rf /app/server/*.log
RUN rm -rf /app/server/src
RUN rm -rf /app/server/upload
EXPOSE 3002
CMD ["sh", "-c", "server/start.sh"]