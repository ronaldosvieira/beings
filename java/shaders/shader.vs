#version 120

attribute vec3 vertices;
attribute vec2 textures;

varying vec2 texCoords;

void main() {
	texCoords = textures;
	gl_Position = vec4(vertices, 1);
}