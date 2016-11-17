#version 120

uniform sampler2D sampler;

varying vec2 texCoords;

void main() {
	gl_FragColor = texture2D(sampler, texCoords);
}