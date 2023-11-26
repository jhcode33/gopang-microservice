FROM node:14.15 as builder

# Set the working directory
# RUN mkdir -p /app
WORKDIR /app

# Copy only the package files to install dependencies
COPY /src/angular/package*.json .

# Install dependencies and specific version of Angular CLI
RUN npm install

RUN npm install -g @angular/cli@13

# Copy the rest of the application files
COPY /src/angular/. .

# Build the Angular app
RUN ng build

# Use Nginx as the production server
FROM nginx:latest

# Set the working directory for Nginx
WORKDIR /usr/share/nginx/html

# Copy the built Angular app to the Nginx web root
COPY --from=builder /app/dist/* .

# Expose port 80
EXPOSE 80

# Command to run the Nginx server
CMD ["nginx", "-g", "daemon off;"]

